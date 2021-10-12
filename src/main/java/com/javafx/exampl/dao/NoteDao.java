package com.javafx.exampl.dao;

import com.javafx.exampl.entity.Note;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class NoteDao {

    private static final String URL = "jdbc:mysql://localhost:3306/mydb";
    private static final String USER = "root";
    private static final String PASSWORD = "K50ABseries";

    public static final String INSERT_QUERY = "INSERT note(description, created_time) VALUE (?, ?)";
    public static final String DELETE_QUERY = "DELETE FROM note WHERE id = ?";
    public static final String SELECT_ALL_QUERY = "SELECT id, description, created_time FROM note";

    public Note create(Note note) throws DaoException {
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement =
                    connection.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, note.getDescription());
            Timestamp timestamp = Timestamp.valueOf(note.getCreatedTime());
            preparedStatement.setTimestamp(2, timestamp);
            preparedStatement.execute();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            generatedKeys.next();
            int id = generatedKeys.getInt(1);
            note.setId(id);
            return note;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new DaoException("Failed to connect");
        }
    }

    public void delete(Note node) throws DaoException {
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_QUERY);
            preparedStatement.setInt(1, node.getId());
            preparedStatement.execute();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new DaoException("Failed to connect");
        }
    }

    public List<Note> findAllNote() throws DaoException {

        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_ALL_QUERY);
            List<Note> noteList = new ArrayList<>();
            while (resultSet.next()) {
                Note note = new Note();
                note.setId(resultSet.getInt("id"));
                note.setDescription(resultSet.getString("description"));
                note.setCreatedTime(Timestamp.valueOf(resultSet.getString("created_time")).toLocalDateTime());
                noteList.add(note);
            }
            return noteList;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new DaoException("Failed to connect");
        }
    }

    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
