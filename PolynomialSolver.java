import java.math.BigInteger;
import java.nio.file.*;
import java.util.*;
import org.json.JSONObject;

public class PolynomialSolver {
    public static void main(String[] args) {
        try {
            Scanner sc = new Scanner(System.in);
            System.out.print("Enter JSON file path: ");
            String filePath = sc.nextLine();   // e.g. testcase1.json
            sc.close();

            // Read entire file content into a string
            String jsonInput = new String(Files.readAllBytes(Paths.get(filePath)));

            System.out.println("\n=== SOLUTION ===");
            solvePolynomial(jsonInput);

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void solvePolynomial(String jsonInput) {
        JSONObject obj = new JSONObject(jsonInput);
        int n = obj.getJSONObject("keys").getInt("n");
        int k = obj.getJSONObject("keys").getInt("k");

        List<BigInteger> roots = new ArrayList<>();
        for (int i = 1; i <= k; i++) {
            JSONObject rootObj = obj.getJSONObject(String.valueOf(i));
            int base = Integer.parseInt(rootObj.getString("base"));
            String value = rootObj.getString("value");
            BigInteger root = new BigInteger(value, base);
            roots.add(root);
        }

        List<BigInteger> coeffs = new ArrayList<>();
        coeffs.add(BigInteger.ONE);

        for (BigInteger r : roots) {
            List<BigInteger> newCoeffs = new ArrayList<>(Collections.nCopies(coeffs.size() + 1, BigInteger.ZERO));
            for (int i = 0; i < coeffs.size(); i++) {
                newCoeffs.set(i, newCoeffs.get(i).add(coeffs.get(i).negate().multiply(r)));
                newCoeffs.set(i + 1, newCoeffs.get(i + 1).add(coeffs.get(i)));
            }
            coeffs = newCoeffs;
        }

        Collections.reverse(coeffs);
        System.out.println("Polynomial coefficients: " + coeffs);
    }
}
