package by.vsu.kovzov.repository;

import by.vsu.kovzov.model.ReceivedMark;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ReceivedMarkRepository {
    @ResultMap("receivedMarkResultMap")
    @Select("SELECT rm.id, rm.date, mark_id, value_int, value_str, mark_scale FROM received_mark rm " +
            "LEFT JOIN mark m on rm.mark_id = m.id " +
            "WHERE rm.student_id = ${studentId}")
    List<ReceivedMark> findAllByStudentId(@Param("studentId") Long studentId);
}
