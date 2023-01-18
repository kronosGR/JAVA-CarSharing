package carsharing;

import carsharing.Model.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CustomerDao implements Dao {
    private static Connection connection = H2.getConnection();

    private static String Q_SELECT_ALL = "select * from customer";
    private static String Q_SELECT_ALL_BY_CAR = Q_SELECT_ALL + " where rented_car_id=?";
    private static String Q_SELECT_ALL_BY_NAME = Q_SELECT_ALL + " where name=?";
    private static String Q_CREATE = "insert into customer (name) values (?);";
    private static String Q_UPDATE = "update customer set name=?, rented_car_id=? where id=?;";


    @Override
    public List<Customer> getAll() {
        List<Customer> customers = new ArrayList<>();
        try {
            ResultSet resultSet = connection.createStatement().executeQuery(Q_SELECT_ALL);
            while (resultSet.next()) {
                customers.add(new Customer(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getInt("rented_car_id")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error while getting all customers");
        }

        return customers;
    }

    @Override
    public boolean create(Object obj) {
        Customer tmp = (Customer) obj;
        try (PreparedStatement preparedStatement = connection.prepareStatement(Q_CREATE)) {
            preparedStatement.setString(1, tmp.getName());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error while creating customer");
            return false;
        }
        return true;
    }

    @Override
    public boolean update(Object obj) {
        Customer tmp = (Customer)obj;
        try(PreparedStatement preparedStatement = connection.prepareStatement(Q_UPDATE)){
            preparedStatement.setString(1,tmp.getName());

            if (tmp.getRentedCarId()==0) preparedStatement.setNull(2, Types.INTEGER);
            else preparedStatement.setInt(2, tmp.getRentedCarId());

            preparedStatement.setInt(3, tmp.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error while updating customer");
            return false;
        }
        return true;
    }

    @Override
    public boolean delete(long id) {
        // no need for this one
        return false;
    }

    @Override
    public Optional getByName(String name) {
        Optional<Customer> customer = Optional.empty();
        try (PreparedStatement preparedStatement = connection.prepareStatement(Q_SELECT_ALL_BY_NAME)) {
            preparedStatement.setString(1, name);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                customer = Optional.of(new Customer(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getInt("rented_car_id")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error while getting customer");
        }
        return customer;
    }
}
