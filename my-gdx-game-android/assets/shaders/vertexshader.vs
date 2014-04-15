attribute vec4 a_position;
attribute vec4 a_color;
attribute vec2 a_texCoord0;

uniform mat4 u_projTrans;
uniform vec3 u_playerPos;
uniform vec2 u_resolution;

varying vec4 v_color;
varying vec2 v_texCoords;
varying vec3 v_playerPos;
varying vec2 v_resolution;

void main() {
    v_color = a_color;
    v_texCoords = a_texCoord0;
	v_playerPos = u_playerPos;
	v_resolution = u_resolution;
    gl_Position = u_projTrans * a_position;
}