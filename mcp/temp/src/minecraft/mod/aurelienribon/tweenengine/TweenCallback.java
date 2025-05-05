package aurelienribon.tweenengine;

import aurelienribon.tweenengine.BaseTween;

public interface TweenCallback {

   int BEGIN = 1;
   int START = 2;
   int END = 4;
   int COMPLETE = 8;
   int BACK_BEGIN = 16;
   int BACK_START = 32;
   int BACK_END = 64;
   int BACK_COMPLETE = 128;
   int ANY_FORWARD = 15;
   int ANY_BACKWARD = 240;
   int ANY = 255;


   void onEvent(int var1, BaseTween<?> var2);
}
