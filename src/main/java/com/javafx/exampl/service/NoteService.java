package com.javafx.exampl.service;

import com.javafx.exampl.dao.DaoException;
import com.javafx.exampl.dao.NoteDao;
import com.javafx.exampl.entity.Note;

import java.util.List;

public class NoteService {

    private NoteDao noteDao = new NoteDao();

    public Note create(Note note) throws ServiceException {
        try {
            return noteDao.create(note);
        } catch (DaoException e) {
            e.printStackTrace();
            throw new ServiceException("failed to save");
        }
    }

    public void delete(Note note) throws ServiceException {
        try {
            noteDao.delete(note);
        } catch (DaoException e) {
            e.printStackTrace();
            throw new ServiceException("failed to delete from Database");
        }
    }

    public List<Note> findAll() throws  ServiceException {
        try {
            return noteDao.findAllNote();
        } catch (DaoException e) {
            e.printStackTrace();
            throw new ServiceException("failed to read from Database");
        }
    }

}
