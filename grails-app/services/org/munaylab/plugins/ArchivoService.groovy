package org.munaylab.plugins

import grails.gorm.transactions.Transactional

@Transactional
class ArchivoService {

    Archivo actualizarArchivo(ArchivoCommand command) {
        if (command?.accion == 'upload') {
            return subir(command)
        } else if (command?.accion == 'delete') {
            return borrar(command)
        } else {
            return null
        }
    }

    Archivo subir(ArchivoCommand command) {
        Archivo archivo = null
        if (command.validate()) {
            archivo = command.id ? Archivo.get(command.id) : new Archivo()
            archivo = archivo.with {
                nombre          = command.nombre
                fileBytes       = command.file.bytes
                fileContentType = command.file.contentType
                it
            }
            archivo.save()
        } else {
            archivo = archivoConError(command.errors.fieldError.code)
        }
        archivo
    }

    Archivo borrar(ArchivoCommand command) {
        Archivo archivo = null
        if (command.validate()) {
            archivo = Archivo.get(command.id)
            if (archivo) {
                archivo.delete()
                archivo = null
            } else {
                archivo = archivoConError('error.id.not.found', 'id')
            }
        } else {
            archivo = archivoConError(command.errors.fieldError.code)
        }
        archivo
    }

    private Archivo archivoConError(String errorCode, String field = null) {
        Archivo archivo = new Archivo()
        if (field) {
            archivo.errors.rejectValue(field, errorCode)
        } else {
            archivo.errors.reject(errorCode)
        }
        archivo
    }

    @Transactional(readOnly = true)
    Archivo obtener(Long id) {
        Archivo.get(id)
    }

}
