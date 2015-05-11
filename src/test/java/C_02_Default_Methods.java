import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Default methods have been introduced to make the old Java API compatible with the new concepts.
 */
public class C_02_Default_Methods {

    @Test
    public void interfaceWithDefaultMethod() {
        InterfaceWithDefaultMethod i = new DefaultMethodImplementingEmptyClass();

        // Default:
        assertEquals(45, i.addStuff(3));

        // Overwrite:
        InterfaceWithDefaultMethod inter2 = new DefaultMethodOverridingClass();
        assertEquals(15, inter2.addStuff(3));
    }

    @Test
    public void functionalInterfaceAndDefaultMethodTogether() {

        HighlySophisticatedFunctionalInterface inter = () -> 42;

        assertEquals(42, inter.returnAnswerToUltimateQuestionOfLifeTheUniverseAndEverything());
        assertEquals("UH-OH", inter.returnQuestionTo42());
    }

    /**
     * Default methods will be overridden by lower implementations.
     */
    @Test
    public void overridingDefaultMethods() {
        InterfaceWithDefaultMethodGen2 gen2 = new InterfaceWithDefaultMethodGen2() {
        };
        System.out.println(gen2.getSomeString());
    }

    /**
     * This class inherits unrelated defaults for the method foo() and thus cannot exist:
     */
//    class NameConflicts implements DefaultMethodSame1, DefaultMethodSame2 {
//    }



    // Let's take a break here.
    //
    // Default methods seem to be a really nice add-on to the language. However, they have been introduced mainly
    // to make the new Stream-API possible. The interface Iterable got the new method forEach(). This new method has
    // a default implementation. Otherwise, all classes implementing Iterable would have had to be rewritten with an
    // implementation of this new method.
    // The common developer may use default methods, but there are several blog posts  that suggest not to use them
    // as a regular tool. Doing so could result in a mess if your inheritance hierarchy is more complex. Imagine
    // having to "climb up" the inheritance tree to find out what interface overrides which default method. That's
    // just one additional layer of complexity. To avoid problems here, continue implementing interfaces. Use default
    // methods only for what they have been introduced: Enhancing old APIs that have to be downwards compatible.

    // The above is the reason there are no exercises in this chapter.

    // Repetition is a source of learning:
    // What is a functional interface?
    // Is the annotation @FunctionalInterface necessary?
    // What is a lambda?
    // What means "effectively final"?
}
