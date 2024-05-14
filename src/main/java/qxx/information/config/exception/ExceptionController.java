package qxx.information.config.exception;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import qxx.information.config.enums.DataEnums;

/**
 * @author qtx
 * @since 2024/3/19
 */
@RestController
@RequestMapping("/exception")
public class ExceptionController {

    @RequestMapping("/jwtException")
    public void test(HttpServletRequest request) {
        if (request.getAttribute("jwtException") instanceof DataException) {
            throw ((DataException) request.getAttribute("jwtException"));
        } else {
            throw new DataException(DataEnums.FAILED);
        }
    }
}
