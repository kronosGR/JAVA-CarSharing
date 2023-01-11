package carsharing;

import carsharing.Model.Company;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CompanyDao implements Dao{

    private static Connection connection = H2.getConnection();
    private List<Company> companies;

    private static String Q_SELECT_ALL ="select * from company";
    private static String Q_CREATE = "insert into company (name) values (?)";

    public CompanyDao() {
        this.companies = new ArrayList<>();
    }

    @Override
    public List<Company> getAll() {
        try {
            companies.clear();
            ResultSet resultSet = connection.createStatement().executeQuery(Q_SELECT_ALL);
            while (resultSet.next()){
                companies.add(new Company(resultSet.getInt("id"), resultSet.getString("name")));
            }
        } catch (Exception e){
            System.out.println("Error while getting all companies");
        }
        return companies;
    }

    @Override
    public boolean create(Company object) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(Q_CREATE)){
            preparedStatement.setString(1, object.getName());
            preparedStatement.executeUpdate();
        } catch (SQLException e){
            System.out.println("Error while inserting company");
            return false;
        }
        return true;
    }
}
