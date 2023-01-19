package carsharing;

import carsharing.Model.Company;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CompanyDao implements Dao {

    private static Connection connection = H2.getConnection();
    private List<Company> companies;

    private static String Q_SELECT_ALL = "select * from company";
    private static String Q_SELECT_ALL_BY_ID = Q_SELECT_ALL + " where id=?";
    private static String Q_CREATE = "insert into company (name) values (?)";

    public CompanyDao() {
        this.companies = new ArrayList<>();
    }

    @Override
    public List<Company> getAll() {
        try {
            companies.clear();
            ResultSet resultSet = connection.createStatement().executeQuery(Q_SELECT_ALL);
            while (resultSet.next()) {
                companies.add(new Company(resultSet.getInt("id"), resultSet.getString("name")));
            }
        } catch (Exception e) {
            System.out.println("Error while getting all companies");
        }
        return companies;
    }

    @Override
    public boolean create(Object obj) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(Q_CREATE)) {
            preparedStatement.setString(1, ((Company) obj).getName());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error while inserting company");
            return false;
        }
        return true;
    }

    @Override
    public boolean delete(long id) {
        return false;
    }

    @Override
    public boolean update(Object obj) {
        return false;
    }

    @Override
    public Optional getByName(String name) {
        return Optional.empty();
    }

    public Optional<Company> getById(long id) {
        Optional<Company> company = Optional.empty();
        try (PreparedStatement preparedStatement = connection.prepareStatement(Q_SELECT_ALL_BY_ID)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                company = Optional.of(new Company(
                        resultSet.getInt("id"),
                        resultSet.getString("name")));
            }

        } catch (Exception e) {
            System.out.println("Error while getting the company");
            e.printStackTrace();
        }
        return company;
    }
}
