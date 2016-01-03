package spreadsheet.test.api.value;

import spreadsheet.api.value.Value;
import spreadsheet.api.value.ValueVisitor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created by zhaow on 1/4/2016.
 * SimpleExcel
 */
public class ValueAsserts {
    public static void assertIsLoop(Value target) {
        String prefix = "assertIsLoop: ";
        target.visit(new ValueVisitor() {
            @Override
            public void visitLoop() {
                // Yay!
            }

            @Override
            public void visitInvalid(String exp) {
                fail(prefix + "got Invalid " + exp);
            }

            @Override
            public void visitNumber(double val) {
                fail(prefix + "got Number " + val);
            }

            @Override
            public void visitString(String exp) {
                fail(prefix + "got String " + exp);
            }
        });
    }

    public static void assertEqualsInvalid(Value target, String expect) {
        String prefix = String.format("assertEqualsInvalid: %s ", expect);
        target.visit(new ValueVisitor() {
            @Override
            public void visitLoop() {
                fail(prefix + "got Loop");
            }

            @Override
            public void visitInvalid(String exp) {
                assertEquals(prefix + "ne Invalid " + exp, expect, exp);
            }

            @Override
            public void visitNumber(double val) {
                assertTrue(prefix + "ne Number " + val, Double.parseDouble(expect) == val);
            }

            @Override
            public void visitString(String exp) {
                assertEquals(prefix + "ne String " + exp, expect, exp);
            }
        });
    }

    public static void assertEqualsNumber(Value target, double expect) {
        String prefix = String.format("assertEqualsNumber: %f ", expect);
        target.visit(new ValueVisitor() {
            @Override
            public void visitLoop() {
                fail(prefix + "got Loop.");
            }

            @Override
            public void visitInvalid(String exp) {
                assertEquals(prefix + "ne Invalid " + exp, Double.toString(expect), exp);
            }

            @Override
            public void visitNumber(double val) {
                assertTrue(prefix + "ne Number " + val, expect == val);
            }

            @Override
            public void visitString(String exp) {
                assertEquals(prefix + "ne String " + exp, Double.toString(expect), exp);
            }
        });
    }

    public static void assertEqualsString(Value target, String expect) {
        String prefix = String.format("assertEqualsString: %s ", expect);
        target.visit(new ValueVisitor() {
            @Override
            public void visitLoop() {
                fail(prefix + "got Loop.");
            }

            @Override
            public void visitInvalid(String exp) {
                assertEquals(prefix + "ne Invalid " + exp, expect, exp);
            }

            @Override
            public void visitNumber(double val) {
                assertTrue(prefix + "ne Number " + val, Double.parseDouble(expect) == val);
            }

            @Override
            public void visitString(String exp) {
                assertEquals(prefix + "ne String " + exp, expect, exp);
            }
        });
    }
}
