package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {

    @Select("SELECT * FROM NOTES WHERE userid = #{userid}")
    List<Note> usernotes(Integer userid);

    @Insert("INSERT INTO NOTES (noteid, notetitle, notedescription, userid) VALUES(#{noteid}, #{notetitle}, #{notedescription}, #{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "noteid")
    int addnote(Note note);

    @Update("UPDATE NOTES SET notetitile = #{notetitle}, notedescription = #{notedescription} WHERE noteid = #{noteid}")
    int updatenote(Integer nid, String title, String description);

    @Delete("DELETE FROM NOTES WHERE noteid = #{noteid}")
    int deletenote(Integer noteid);

    @Select("SELECT noteid from NOTES WHERE notetitle = #{notetitle}")
    int nid(String notetitle);
}
