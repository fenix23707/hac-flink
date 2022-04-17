package by.vsu.kovzov.repository;

import by.vsu.kovzov.model.ReceivedMark;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ReceivedMarkRepository {
    @ResultMap("receivedMarkResultMap")
    @Select("SELECT rm.id, rm.date, mark_id, value_int, value_str, mark_scale, discipline_id, shortname, fullname " +
            "FROM received_mark rm " +
            "LEFT JOIN mark m ON rm.mark_id = m.id " +
            "LEFT JOIN discipline d ON d.id = rm.discipline_id " +
            "WHERE rm.student_id = ${studentId} AND " +
            "m.mark_scale = 0")
    List<ReceivedMark> findAllByStudentId(@Param("studentId") Long studentId);
}
