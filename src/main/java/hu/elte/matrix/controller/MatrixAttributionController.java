package hu.elte.matrix.controller;

import hu.elte.matrix.exception.DimensionException;
import hu.elte.matrix.exception.InverseException;
import hu.elte.matrix.model.Matrix;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class MatrixAttributionController {

    @Autowired
    private Environment env;

    @RequestMapping(value = "/api/matrix/inverse", method = RequestMethod.POST,
            consumes = "application/json")
    public String getMatrixInverse(Map<String, Object> model, @RequestBody Matrix matrix) {
        Matrix inverseMatrix;
        try {
            inverseMatrix = matrix.getInverse();
        } catch (DimensionException e) {
            e.printStackTrace();
            String errorMessage = env.getProperty("message.attribution.dimensionException");
            model.put("errorMessage", errorMessage);
            return "resultError";
        } catch (InverseException e) {
            String errorMessage = env.getProperty("message.inverseException");
            model.put("errorMessage", errorMessage);
            return "resultError";
        }
        model.put("resultMatrix", inverseMatrix);

        return "resultMatrix";
    }

    @RequestMapping(value = "/api/matrix/determinant", method = RequestMethod.POST,
            consumes = "application/json")
    public String getMatrixDeterminant(Map<String, Object> model, @RequestBody Matrix matrix) {
        double determinant;
        try {
            determinant = matrix.getDeterminant();
        } catch (DimensionException e) {
            e.printStackTrace();
            String errorMessage = env.getProperty("message.attribution.dimensionException");
            model.put("errorMessage", errorMessage);
            return "resultError";
        }
        model.put("result", determinant);

        return "resultNumber";
    }

    @RequestMapping(value = "/api/matrix/rank", method = RequestMethod.POST,
            consumes = "application/json")
    public String getMatrixRank(Map<String, Object> model, @RequestBody Matrix matrix) {
        double rank = matrix.getRank();
        model.put("result", rank);

        return "resultNumber";
    }
}
