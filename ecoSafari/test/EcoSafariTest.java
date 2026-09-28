package test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import domain.*;

/**
 * The test class Test.
 */
public class EcoSafariTest
{
    private EcoSafari safari;
    private Storm storm;

    public EcoSafariTest()
    {
    }

    @BeforeEach
    public void setUp()
    {
        safari = new EcoSafari();
        storm = new Storm(safari, 10, 10);
    }

    @Test
    public void shouldMoveDiagonally()
    {
        storm.tic();

        assertNull(safari.get(10, 10));
        assertSame(storm, safari.get(9, 11));
    }

    @Test
    public void shouldDestroyEntity()
    {
        Bush bush = new Bush(safari, 9, 11);

        storm.tic();

        assertSame(storm, safari.get(9, 11));
    }
    
    @Test
    public void shouldBothHungryElephantsEat() {
        HungryElephant vargas = new HungryElephant(safari, 10, 5);
        HungryElephant barbosa = new HungryElephant(safari, 15, 15);
    
        new Bush(safari, 11, 6);
        new Bush(safari, 16, 16);
    
        vargas.tic();
        barbosa.tic();
    
        assertSame(vargas, safari.get(11, 6));
        assertSame(barbosa, safari.get(16, 16));
    }
    
    @Test
    public void shouldEatElephantFromFarAway()
    {
        Sabertooth sabertooth = new Sabertooth(safari, 10, 10);
        Elephant elephant = new Elephant(safari, 10, 13);

        sabertooth.tic();

        assertSame(sabertooth, safari.get(10, 13));
        assertEquals(100, sabertooth.getEnergy());
    }

    @Test
    public void shouldMoveTwoCellsAndLoseFiveEnergy()
    {
        Sabertooth sabertooth = new Sabertooth(safari, 10, 10);

        sabertooth.tic();

        assertNull(safari.get(10, 10));
        assertSame(sabertooth, safari.get(12, 12));
        assertEquals(95, sabertooth.getEnergy());
    }
    
    @Test
    public void shouldBothSabertoothsEatDifferentElephants()
    {
        Sabertooth vargas = new Sabertooth(safari, 10, 10);
        Sabertooth barbosa = new Sabertooth(safari, 20, 20);
    
        new Elephant(safari, 10, 13);
        new Elephant(safari, 17, 17);
    
        vargas.tic();
        barbosa.tic();
    
        assertSame(vargas, safari.get(10, 13));
        assertSame(barbosa, safari.get(17, 17));
    }
}
