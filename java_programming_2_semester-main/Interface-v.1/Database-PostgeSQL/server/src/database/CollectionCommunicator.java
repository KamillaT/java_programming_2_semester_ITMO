package database;

import data.*;
import org.intellij.lang.annotations.Language;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class CollectionCommunicator {
    @Language("SQL")
    static final String ADD_QUERY = """
            WITH PRODUCT_COORDINATES AS (
                -- CREATING COORDINATES
                INSERT INTO COORDINATES (X, Y)
                    VALUES (?, ?)
                    RETURNING COORDINATES.ID),
                 -- CREATING ADDRESS FOR ORGANIZATION
                 ORGANIZATION_ADDRESS AS (
                     INSERT INTO ADDRESS (STREET, ZIP_CODE)
                         VALUES (?, ?)
                         RETURNING ADDRESS.ID),
                 -- CREATING ORGANIZATION FOR PRODUCT
                 CREATED_ORGANIZATION AS (
                     INSERT INTO ORGANIZATION
                         (NAME,
                          ANNUAL_TURNOVER,
                          ORGANIZATION_TYPE_ID,
                          ADDRESS_ID)
                         VALUES (?,
                                 ?,
                                 (SELECT ID
                                  FROM ORGANIZATION_TYPE
                                  WHERE ORGANIZATION_TYPE.NAME = ?),
                                 (SELECT ID FROM ORGANIZATION_ADDRESS))
                         RETURNING ORGANIZATION.ID)
            INSERT
            -- CREATING PRODUCT
            INTO PRODUCT (NAME,
                          COORDINATES_ID,
                          CREATION_DATE,
                          PRICE,
                          UNIT_OF_MEASURE_ID,
                          MANUFACTURER, CREATOR_ID)
            VALUES (?,
                    (SELECT ID FROM PRODUCT_COORDINATES),
                    ?,
                    ?,
                    (SELECT ID
                     FROM UNIT_OF_MEASURE
                     WHERE UNIT_OF_MEASURE.NAME = ?),
                    (SELECT ID FROM CREATED_ORGANIZATION),
                    ?)
            RETURNING PRODUCT.ID, PRODUCT.manufacturer
            """;

    @Language("SQL")
    static final String UPDATE_QUERY = """
            SELECT UPDATE_PRODUCT(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;

    @Language("SQL")
    static final String DELETE_PRODUCT_BY_ID_QUERY = """
            DELETE
            FROM PRODUCT
            WHERE ID = ? AND CREATOR_ID = ?;
            """;

    @Language("SQL")
    static final String DELETE_PRODUCT_BY_PRICE_QUERY = """
            DELETE
            FROM PRODUCT
            WHERE PRICE = ? AND CREATOR_ID = ?;
            """;

    @Language("SQL")
    static final String LOAD_COLLECTION_QUERY = """
            SELECT PRODUCT.ID,
                   PRODUCT.NAME,
                   COORDINATES.X,
                   COORDINATES.Y,
                   PRODUCT.CREATION_DATE,
                   PRODUCT.PRICE,
                   PRODUCT.UNIT_OF_MEASURE_ID,
                   PRODUCT.CREATOR_ID,
                   ORGANIZATION.ID AS O_ID,
                   ORGANIZATION.NAME AS O_NAME,
                   ORGANIZATION.ANNUAL_TURNOVER,
                   ORGANIZATION.ORGANIZATION_TYPE_ID,
                   ADDRESS.STREET,
                   ADDRESS.ZIP_CODE
            FROM PRODUCT
                     JOIN COORDINATES ON COORDINATES.ID = PRODUCT.COORDINATES_ID
                     JOIN ORGANIZATION ON ORGANIZATION.ID = PRODUCT.MANUFACTURER
                     JOIN ADDRESS ON ADDRESS.ID = ORGANIZATION.ADDRESS_ID;
            """;

    @Language("SQL")
    static final String CLEAR_ALL_USERS_PRODUCTS = """
            DELETE FROM PRODUCT WHERE PRODUCT.CREATOR_ID = ?
            """;

    final DatabaseManager databaseManager;

    public CollectionCommunicator(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    public ActionResult removeProductById(Integer id, Integer userId) {
        try {
            databaseManager.executeUpdate(DELETE_PRODUCT_BY_ID_QUERY, id, userId);
        } catch (Exception e) {
            return ActionResult.FAILURE;
        }

        return ActionResult.SUCCESS;
    }

    public ActionResult removeProductByPrice(Float price, Integer userId) {
        try {
            databaseManager.executeUpdate(DELETE_PRODUCT_BY_PRICE_QUERY, price, userId);
        } catch (Exception e) {
            return ActionResult.FAILURE;
        }

        return ActionResult.SUCCESS;
    }

    public ActionResult removeAllUsersProducts(int userId) {
        try {
            databaseManager.executeUpdate(CLEAR_ALL_USERS_PRODUCTS, userId);
        } catch (Exception e) {
            return ActionResult.FAILURE;
        }

        return ActionResult.SUCCESS;
    }

    public List<Product> loadProducts() {
        List<Product> loadedProducts = new ArrayList<>();

        try {
            databaseManager.executeQuery(
                    resultSet ->
                    {
                        Coordinates coordinates = new Coordinates(
                                (Double) resultSet.getObject("X"),
                                (Double) resultSet.getObject("y")
                        );

                        Address address = new Address(
                                (String) resultSet.getObject("STREET"),
                                (String) resultSet.getObject("ZIP_CODE")
                        );

                        Organization organization = new Organization(
                                (Long) resultSet.getObject("O_ID"),
                                (String) resultSet.getObject("O_NAME"),
                                (Double) resultSet.getObject("ANNUAL_TURNOVER"),
                                OrganizationType.valueOf((Integer) resultSet.getObject("ORGANIZATION_TYPE_ID")),
                                address
                        );

                        Product product = new Product(
                                (Integer) resultSet.getObject("ID"),
                                (String) resultSet.getObject("NAME"),
                                coordinates,
                                resultSet.getDate("CREATION_DATE").toLocalDate(),
                                (Float) resultSet.getObject("PRICE"),
                                UnitOfMeasure.valueOf((Integer) resultSet.getObject("UNIT_OF_MEASURE_ID")),
                                organization,
                                (Integer) resultSet.getObject("CREATOR_ID")
                        );

                        loadedProducts.add(product);
                    }
                    ,
                    LOAD_COLLECTION_QUERY
            );
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return loadedProducts;
    }

    public AbstractMap.SimpleEntry<Integer, Long> addProduct(Product product) {
        final var coordinates = product.getCoordinates();
        final var organization = product.getOrganization();
        final var address = organization.getPostalAddress();
        var createdProductId = new AtomicInteger(-1);
        AtomicLong organizationId = new AtomicLong(-1);

        try {
            databaseManager.executeQuery(
                    resultSet -> {
                        createdProductId.set(resultSet.getInt(1));
                        organizationId.set(resultSet.getLong(2));
                    },
                    ADD_QUERY,
                    coordinates.getX(),
                    coordinates.getY(),
                    address.getStreet(),
                    address.getZipCode(),
                    organization.getName(),
                    organization.getAnnualTurnover(),
                    organization.getType().toString(),
                    product.getName(),
                    product.getCreationDate(),
                    product.getPrice(),
                    product.getUnitOfMeasure().toString(),
                    product.getCreatorId()
            );
        } catch (Exception exception) {
            exception.printStackTrace();
            return new AbstractMap.SimpleEntry<>(-1, -1L);
        }

        return new AbstractMap.SimpleEntry<>(createdProductId.get(), organizationId.get());
    }

    public int updateProduct(Product product) {
        final var coordinates = product.getCoordinates();
        final var organization = product.getOrganization();
        final var address = organization.getPostalAddress();
        var createdProductId = new AtomicInteger(-1);

        try {
            databaseManager.executeQuery(
                    resultSet -> createdProductId.set(resultSet.getInt(1)),
                    UPDATE_QUERY,
                    product.getId(),
                    coordinates.getX(),
                    coordinates.getY(),
                    address.getStreet(),
                    address.getZipCode(),
                    organization.getName(),
                    organization.getAnnualTurnover(),
                    organization.getType().toString(),
                    product.getName(),
                    product.getPrice(),
                    product.getUnitOfMeasure().toString()
            );
        } catch (Exception exception) {
            return -1;
        }

        return createdProductId.get();

    }
}
