package com.aluracursos.challengelibros.service;

public interface IConvierteDatos {
    <T> T obtenerDatos(String json, Class<T> clase);
}
