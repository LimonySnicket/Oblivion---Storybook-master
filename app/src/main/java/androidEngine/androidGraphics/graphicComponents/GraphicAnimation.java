package androidEngine.androidGraphics.graphicComponents;

import android.graphics.Bitmap;
import android.graphics.Matrix;

import androidEngine.androidGraphics.AndroidConfig;

/**
 * Created by andrew on 10/21/17.
 */

public class GraphicAnimation {
    protected static AndroidConfig config;
    protected Bitmap originalSheet;
    protected Bitmap spriteSheet;
    protected Bitmap frame;
    protected int currentFrame;
    protected int frameHeight;
    protected int screenHeight;
    protected int frameWidth;
    protected int screenWidth;
    protected int frameColumn;
    protected int numberOfFrames;

    protected int[][] animationData;
    protected double animationTime;
    protected int animationNumber;

    //
    //   Builder Class
    //
    public static class Builder {
        private final Bitmap spritesheet;

        private int frameWidth, frameHeight;
        private int frameColumn;
        private int numberofFrames;

        public Builder(Bitmap image) {
            spritesheet = image;
        }
        public Builder setWidth(int w) {
            frameWidth = w; return this;
        }
        public Builder setHeight(int h) {
            frameHeight = h; return this;
        }
        public Builder setNumberOfColumns(int num) {
            frameColumn = num; return this;
        }
        public Builder setNumberOfFrames(int num) {
            numberofFrames = num; return this;
        }

        public GraphicAnimation build() {
            return new GraphicAnimation(this);
        }
    }

    //
    //   Initializing Animation
    //   Advised against using
    //
    public GraphicAnimation(Bitmap s, int w, int h, int num, int column) {
        originalSheet = s;
        frameHeight = h;
        frameWidth = w;
        numberOfFrames = num;
        frameColumn = column;
        setFrame(0);
        updateBitmaps();
    }

    //Builder Instantiation
    private GraphicAnimation(Builder builder) {
        this(builder.spritesheet, builder.frameWidth, builder.frameHeight,
        builder.numberofFrames, builder.frameColumn);
    }

    //Copying Animation
    public GraphicAnimation(GraphicAnimation ga) {
        try {
            spriteSheet = ga.spriteSheet;
        } catch (Exception e) {}
        frameHeight = ga.frameHeight;
        frameWidth = ga.frameWidth;
        numberOfFrames = ga.numberOfFrames;
        frameColumn = ga.frameColumn;
        setFrame(0);
        updateBitmaps();
    }

    //
    //   setting up animation
    //

    public void setAnimationData(int[][] data) {
        animationData = data;
        animationTime = 0;
        animationNumber = 0;
    }

    public void setAnimationNumber(int number) {
        animationNumber = number;
    }

    public void updateAnimation(double time) {
        if(animationData != null) {
            animationTime += time;
            while (animationTime > animationData[animationNumber][0]) {
                animationTime -= animationData[animationNumber][0];
                if (currentFrame < (animationData[animationNumber][1] + animationData[animationNumber][2])) {
                    nextFrame();
                } else {
                    setFrame(animationData[animationNumber][1]);
                }
            }
        }
    }

    //
    //   Changing Animation Frames
    //
    public void setFrame(int frameNumber) {
        if(spriteSheet != null) {
            updateBitmaps();
            if (frameNumber < numberOfFrames && frameNumber >= 0) {
                currentFrame = frameNumber;
                int row = (frameNumber) % frameColumn;
                int column = (frameNumber) / frameColumn;
                if(frame != null) {
                    frame.recycle();
                }
                frame = Bitmap.createBitmap(spriteSheet, row * screenWidth, column * screenHeight, screenWidth, screenHeight);
            } else {
                System.out.println("Error: Can't change Frames");
            }
        } else {
            frame = null;
        }
    }

    public void nextFrame(){
        if(currentFrame + 1 < numberOfFrames) {
            setFrame(currentFrame + 1);
        }else{
            setFrame(0);
        }
    }

    //
    //    Borrowed code to resize Bitmap
    //
    public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);

        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
    }

    public void updateBitmaps() {
        if(config != null) {
            screenWidth = (int) (frameWidth * config.getAspectRatio());
            screenHeight = (int) (frameHeight * config.getAspectRatio());
            spriteSheet = getResizedBitmap(originalSheet, screenWidth * frameColumn, screenHeight * (numberOfFrames / frameColumn));
        }
    }

    //
    //    Set Android Config
    //

    public static void setConfig(AndroidConfig con) {
        config = con;
    }

    //
    //    Setting SpriteSheet
    //
    public void setOriginalSheet(Bitmap original) {
        originalSheet = original;
        updateBitmaps();
    }
    //
    //   Return methods
    //
    public int getHeight() {
        return frameHeight;
    }
    public int getWidth() {
        return frameWidth;
    }
    //Returning Frame
    public Bitmap getFrame() {
        return frame;
    }
    public Bitmap getFlippedFrame() {
        Matrix m = new Matrix();
        m.preScale(-1, 1);
        return Bitmap.createBitmap(frame, 0, 0, frame.getWidth(), frame.getHeight(), m, false);
    }

    public String toString() {
        if(spriteSheet != null) {
            return "There Is an Image";
        } else {
            return "Empty Graphic Animation";
        }
    }
}
