package org.viewer1;

import org.joml.Vector3f;

public class Vertex {
   final int MAX_BONE_INFLUENCE =4;
    Vector3f Position;

    Vector3f Normal;

    Vector3f TexCoords;

    Vector3f Tangent;
    // bitangent
    Vector3f Bitangent;
    //bone indexes which will influence this vertex
    int[] m_BoneIDs=new int[MAX_BONE_INFLUENCE];
    //weights from each bone
    float[] m_Weights=new float[MAX_BONE_INFLUENCE];
}
