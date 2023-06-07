package App;

import Engine.Camera;
import org.joml.Math;
import org.joml.Vector3f;

public class Player {
    private Camera camera;
    private int hitDistance = 5;
    private int blockToPlaceType = 2;

    public Player(Vector3f position) {
        camera = new Camera(position);
    }

    public void Move() {
        camera.Move();
    }

    public void Rotate() {
        camera.Rotate();
    }

    public Camera getView() {
        return camera;
    }

    public void destroyBlock(World world) {
        for (float i = 0; i <= hitDistance; i += 0.1f) {
            Vector3f offset = new Vector3f(camera.getDirection()).normalize();
            Vector3f blockPos = new Vector3f(camera.getPosition());
            blockPos.add(new Vector3f(offset).mul(i));
            blockPos.round();

            if (world.getBlock(blockPos) != null && world.getBlock(blockPos).getType() != 0) {
                world.getBlock(blockPos).destroy();
                return;
            }
        }
    }

    public void placeBlock(World world) {
        Vector3f[] normals = new Vector3f[] {
            new Vector3f(1.0f, 0.0f, 0.0f),
            new Vector3f(-1.0f, 0.0f, 0.0f),
            new Vector3f(0.0f, 1.0f, 0.0f),
            new Vector3f(0.0f, -1.0f, 0.0f),
            new Vector3f(0.0f, 0.0f, 1.0f),
            new Vector3f(0.0f, 0.0f, -1.0f) };

        for (float i = 0; i <= hitDistance; i += 0.1f) {
            Vector3f offset = new Vector3f(camera.getDirection()).normalize();
            Vector3f blockPos = new Vector3f(camera.getPosition());
            blockPos.add(new Vector3f(offset).mul(i));
            blockPos.round();

            Block block = world.getBlock(blockPos);
            if (block == null || block.getType() == 0)
                continue;

            Vector3f[] sidePoints = new Vector3f[] {
                new Vector3f(blockPos.x + 0.5f, blockPos.y, blockPos.z),
                new Vector3f(blockPos.x - 0.5f, blockPos.y, blockPos.z),
                new Vector3f(blockPos.x, blockPos.y + 0.5f, blockPos.z),
                new Vector3f(blockPos.x, blockPos.y - 0.5f, blockPos.z),
                new Vector3f(blockPos.x, blockPos.y, blockPos.z + 0.5f),
                new Vector3f(blockPos.x, blockPos.y, blockPos.z - 0.5f)
            };

            float closest = 10000f;
            Vector3f closestNormal = new Vector3f(0.0f, 0.0f, 0.0f);

            for (int j = 0; j < 6; j++) {
                Vector3f p = sideAndVecIntersection(camera.getPosition(), new Vector3f(camera.getPosition()).add(camera.getDirection()),
                                                    sidePoints[j], normals[j]);
                if (p == null)
                    continue;
                if ((j == 0 || j == 1) && sidePoints[j].y + 0.5f >= p.y && sidePoints[j].y - 0.5f <= p.y && sidePoints[j].z + 0.5f >= p.z && sidePoints[j].z - 0.5f <= p.z ||
                    (j == 2 || j == 3) && sidePoints[j].x + 0.5f >= p.x && sidePoints[j].x - 0.5f <= p.x && sidePoints[j].z + 0.5f >= p.z && sidePoints[j].z - 0.5f <= p.z ||
                    (j == 4 || j == 5) && sidePoints[j].x + 0.5f >= p.x && sidePoints[j].x - 0.5f <= p.x && sidePoints[j].y + 0.5f >= p.y && sidePoints[j].y - 0.5f <= p.y) {
                        if (p.distance(camera.getPosition()) < closest) {
                            closest = p.distance(camera.getPosition());
                            closestNormal = new Vector3f(normals[j]);
                        }
                }
            }

            Vector3f newBlockPos = new Vector3f(blockPos).add(closestNormal);
            Block newBlock = world.getBlock(newBlockPos);

            if (newBlock == null || newBlock.getType() != 0)
                return;

            world.getBlock(newBlockPos).set(blockToPlaceType);
        }
    }

    public void changeBlockToPlace(World world) {
        for (float i = 0; i <= hitDistance; i += 0.1f) {
            Vector3f offset = new Vector3f(camera.getDirection()).normalize();
            Vector3f blockPos = new Vector3f(camera.getPosition());
            blockPos.add(new Vector3f(offset).mul(i));
            blockPos.round();
            Block block = world.getBlock(blockPos);

            if (block != null && block.getType() != 0) {
                blockToPlaceType = block.getType();
                return;
            }
        }
    }

    private Vector3f sideAndVecIntersection(Vector3f p1, Vector3f p2, Vector3f sidePoint, Vector3f sideNormal) {
        float epsilon = 1e-6f;
        Vector3f u = new Vector3f(p2).sub(p1);
        float dot = new Vector3f(sideNormal).dot(u);

        if (Math.abs(dot) <= epsilon)
            return null;

        Vector3f w = new Vector3f(p1).sub(sidePoint);
        float fac = -new Vector3f(sideNormal).dot(w) / dot;
        u = u.mul(fac);
        return new Vector3f(p1).add(u);
    }
}

