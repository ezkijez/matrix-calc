package hu.elte.matrix.controller;

import hu.elte.matrix.exception.InverseException;
import hu.elte.matrix.model.Matrix;
import hu.elte.matrix.utils.GaussJordanElimination;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class MatrixAttributionController {

    @RequestMapping(value = "/api/matrix/inverse", method = RequestMethod.POST,
            consumes = "application/json")
    public String getMatrixInverse(Map<String, Object> model, @RequestBody Matrix matrix) {
        Matrix inverseMatrix = null;
        try {
            inverseMatrix = GaussJordanElimination.calculateInverse(matrix);
        } catch (InverseException e) {
            e.printStackTrace();
        }
        model.put("resultMatrix", inverseMatrix);

        return "resultMatrix";
    }

    @RequestMapping(value = "/api/matrix/determinant", method = RequestMethod.POST,
            consumes = "application/json")
    public String getMatrixDeterminant(Map<String, Object> model, @RequestBody Matrix matrix) {
        double determinant = GaussJordanElimination.calculateDeterminant(matrix);
        model.put("result", determinant);

        return "resultNumber";
    }

    @RequestMapping(value = "/api/matrix/rank", method = RequestMethod.POST,
            consumes = "application/json")
    public String getMatrixRank(Map<String, Object> model, @RequestBody Matrix matrix) {
        double rank = GaussJordanElimination.calculateRank(matrix);
        model.put("result", rank);

        return "resultNumber";
    }
}
