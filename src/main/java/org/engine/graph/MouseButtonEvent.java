package org.engine.graph;

import org.joml.Vector2f;

public interface MouseButtonEvent {
      void mouseButtonPressed();
      void mouseButtonReleased();
      void mouseEntered();
      void mouseExit();
      void mouseDrag(float x,float y);
      void mouseMove(float x,float y);
      void mouseScroll(float x,float y);

}
