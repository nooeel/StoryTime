#type vertex
#version 330 core

layout (location=0) in vec3 aPos;
layout (location=1) in vec4 aColor;

uniform mat4 uProjection;
uniform mat4 uView;

out vec4 fColor;

void main() {
    fColor = aColor;
    gl_Position = uProjection * uView * vec4(aPos, 1.0);
}


#type fragment
#version 330 core

uniform float uTime;

in vec4 fColor;

out vec4 color;

void main() {

    color = fColor;         // standard Color rgb



    //float avg = (fColor.r + fColor.g + fColor.b) / 3;     // avg for b+w
    //color = vec4(avg, avg, avg, 1);                       // black and white


    //float noise = fract(sin(dot(fColor.xy, vec2(12.9898, 78.233))) * 43758.5453);   // noice random generator
    //color = fColor * noise;                                                         // noice  für zb wolken


    //color = sin(uTime) * fColor;  //sinwave colored black
}