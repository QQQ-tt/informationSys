package qxx.information.controller;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import qxx.information.config.enums.DataEnums;
import qxx.information.config.exception.DataException;
import qxx.information.entity.SysRole;
import qxx.information.pojo.dto.TestDTO;

/**
 * @author qtx
 * @since 2024/3/19
 */
@RestController
@RequestMapping("/exception")
public class ExceptionController {

    @PostMapping("/jwtException")
    public void test(HttpServletRequest request) {
        if (request.getAttribute("jwtException") instanceof DataException) {
            throw ((DataException) request.getAttribute("jwtException"));
        } else {
            throw new DataException(DataEnums.FAILED);
        }
    }
}
