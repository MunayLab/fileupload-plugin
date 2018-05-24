package org.munaylab.plugins

class ArchivoController {

    static defaultAction = 'show'

    def archivoService

    def show(Long id) {
        Archivo archivo = archivoService.obtener(id, params.nombre)
        if (!archivo) {
            render status: 404
        } else {
            render file: archivo.fileBytes, fileName: archivo.nombre, contentType: archivo.fileContentType
        }
    }

}
