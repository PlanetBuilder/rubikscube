import GLOOP.GLVektor;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class Cube {
    private final CubePart[] cubeParts = new CubePart[27];

    public Cube(double size, double spacing) {
        int i = 0;

        for (int ro = ColourPosition.RED; ro <= ColourPosition.ORANGE; ro++) {
            for (int wy = ColourPosition.WHITE; wy <= ColourPosition.YELLOW; wy++) {
                for (int gb = ColourPosition.GREEN; gb <= ColourPosition.BLUE; gb++) {
                    cubeParts[i] = new CubePart(new CubePartPosition(ro, wy, gb), (size - 2*spacing)/3, spacing);
                    if (ro == 0 && wy == 0 && gb == 0) {
                        //TODO: Cube mid is sphere
                        cubeParts[i] = new CubePart(new CubePartPosition(ro, wy, gb), 2.1*((size - 2*spacing)/3), spacing);
                    }
                    if (ro < 0) cubeParts[i].cubeSides[0].setzeTextur("textures/red.png");    else cubeParts[i].cubeSides[0].setzeTextur("textures/black.png");
                    if (ro > 0) cubeParts[i].cubeSides[1].setzeTextur("textures/orange.png");   else cubeParts[i].cubeSides[1].setzeTextur("textures/black.png");
                    if (wy < 0) cubeParts[i].cubeSides[2].setzeTextur("textures/white.png");      else cubeParts[i].cubeSides[2].setzeTextur("textures/black.png");
                    if (wy > 0) cubeParts[i].cubeSides[3].setzeTextur("textures/yellow.png");   else cubeParts[i].cubeSides[3].setzeTextur("textures/black.png");
                    if (gb < 0) cubeParts[i].cubeSides[4].setzeTextur("textures/green.png");    else cubeParts[i].cubeSides[4].setzeTextur("textures/black.png");
                    if (gb > 0) cubeParts[i].cubeSides[5].setzeTextur("textures/blue.png");     else cubeParts[i].cubeSides[5].setzeTextur("textures/black.png");
                    i++;
                }
            }
        }
    }

    public int getCubePartIndex(CubePartPosition cubePartPosition) {
        for (int i = 0; i < cubeParts.length; i++) {
            if (cubeParts[i].currentPosition.equals(cubePartPosition)) return i;
        }
        return -1;
    }

    public void rotate(Colour colour, int rotationModifier) {
        GLVektor sideCenter = Arrays.stream(cubeParts).filter(cubePart1 -> cubePart1.currentPosition.equals(colour.centerPosition)).collect(Collectors.toList()).get(0).originalVectorPosition;
        long time1 = System.currentTimeMillis();
        for (int i = 0; i < 90; i++)
            for (CubePart cubePart : Arrays.stream(cubeParts).filter(cubePart -> cubePart.currentPosition.toInt() % colour.colourFactor == 0).collect(Collectors.toList())) {
                cubePart.rotate(1, new GLVektor(colour.axis.toVector().x * rotationModifier, colour.axis.toVector().y * rotationModifier, colour.axis.toVector().z * rotationModifier), sideCenter);
                try {
                    TimeUnit.MILLISECONDS.sleep(2);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        System.out.println(System.currentTimeMillis()-time1);

        for (CubePart cubePart : Arrays.stream(cubeParts).filter(cubePart -> cubePart.currentPosition.toInt() % colour.colourFactor == 0).collect(Collectors.toList())) {
            if (colour.colourFactor % ColourFactor.RED == 0) {
                cubePart.currentPosition = new CubePartPosition(ColourPosition.RED, -rotationModifier * cubePart.currentPosition.z(), rotationModifier * cubePart.currentPosition.y());
                for (int i = 0; i < cubePart.sidePositions.length; i++)
                    cubePart.sidePositions[i] = new CubePartPosition(cubePart.sidePositions[i].x(), -rotationModifier * cubePart.sidePositions[i].z(), rotationModifier * cubePart.sidePositions[i].y());
            }
            else if (colour.colourFactor % ColourFactor.ORANGE == 0) {
                cubePart.currentPosition = new CubePartPosition(ColourPosition.ORANGE, -rotationModifier * cubePart.currentPosition.z(), rotationModifier * cubePart.currentPosition.y());
                for (int i = 0; i < cubePart.sidePositions.length; i++)
                    cubePart.sidePositions[i] = new CubePartPosition(cubePart.sidePositions[i].x(), -rotationModifier*cubePart.sidePositions[i].z(), rotationModifier * cubePart.sidePositions[i].y());
            }
            else if (colour.colourFactor % ColourFactor.WHITE == 0) {
                cubePart.currentPosition = new CubePartPosition(rotationModifier * cubePart.currentPosition.z(), ColourPosition.WHITE, -rotationModifier * cubePart.currentPosition.x());
                for (int i = 0; i < cubePart.sidePositions.length; i++)
                    cubePart.sidePositions[i] = new CubePartPosition(rotationModifier * cubePart.sidePositions[i].z(), cubePart.sidePositions[i].y(), -rotationModifier * cubePart.sidePositions[i].x());
            }
            else if (colour.colourFactor % ColourFactor.YELLOW == 0) {
                cubePart.currentPosition = new CubePartPosition(rotationModifier * cubePart.currentPosition.z(), ColourPosition.YELLOW, -rotationModifier * cubePart.currentPosition.x());
                for (int i = 0; i < cubePart.sidePositions.length; i++)
                    cubePart.sidePositions[i] = new CubePartPosition(rotationModifier * cubePart.sidePositions[i].z(), cubePart.sidePositions[i].y(), -rotationModifier * cubePart.sidePositions[i].x());
            }
            else if (colour.colourFactor % ColourFactor.GREEN == 0) {
                cubePart.currentPosition = new CubePartPosition(-rotationModifier * cubePart.currentPosition.y(), rotationModifier * cubePart.currentPosition.x(), ColourPosition.GREEN);
                for (int i = 0; i < cubePart.sidePositions.length; i++)
                    cubePart.sidePositions[i] = new CubePartPosition(-rotationModifier * cubePart.sidePositions[i].y(), rotationModifier * cubePart.sidePositions[i].x(), cubePart.sidePositions[i].z());
            }
            else if (colour.colourFactor % ColourFactor.BLUE == 0) {
                cubePart.currentPosition = new CubePartPosition(-rotationModifier * cubePart.currentPosition.y(), rotationModifier * cubePart.currentPosition.x(), ColourPosition.BLUE);
                for (int i = 0; i < cubePart.sidePositions.length; i++)
                    cubePart.sidePositions[i] = new CubePartPosition(-rotationModifier * cubePart.sidePositions[i].y(), rotationModifier * cubePart.sidePositions[i].x(), cubePart.sidePositions[i].z());
            }
        }
    }

    public CubePartPosition getSidePosition(int cubePartIndex, Colour colour) {
        return cubeParts[cubePartIndex].getSidePosition(colour.colourFactor);
    }
}
