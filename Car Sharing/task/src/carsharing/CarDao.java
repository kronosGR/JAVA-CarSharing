package carsharing;

import carsharing.Model.Car;
import carsharing.Model.Company;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CarDao implements Dao{

    static Connection connection = H2.getConnection();

    static String Q_SELECT_ALL = "select * from car";
    static String Q_SELECT_ALL_BY_COMPANY = Q_SELECT_ALL + " where company_id=?";
    static String Q_CREATE = "insert into car (name, company_id) values(?,?)";

    @Override
    public List getAll() {
        return null;
    }

    @Override
    public boolean create(Object obj) {
        Car tmp = (Car)obj;
        try(PreparedStatement preparedStatement= connection.prepareStatement(Q_CREATE)) {
            preparedStatement.setString(1, tmp.getName());
            preparedStatement.setInt(2, tmp.getCompanyId());
            preparedStatement.execute();
        } catch (SQLException e){
            System.out.println("Error while inserting");
            return false;
        }
        return true;
    }

    public List<Car> getCarsByCompany(Company company){
        List<Car> cars = new ArrayList<>();
        try(PreparedStatement preparedStatement = connection.prepareStatement(Q_SELECT_ALL_BY_COMPANY)){
            preparedStatement.setInt(1, company.getId());
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                cars.add(new Car (resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getInt("company_id")
                        ));
            }
        } catch (SQLException e){
            System.out.println("Error while getting the company's cars");
            return null;
        }
        return cars;
    }
}
