package org.munaylab.plugins

import org.springframework.web.multipart.MultipartFile

class ArchivoCommand implements grails.validation.Validateable {

    Long id
    String nombre
    String accion
    MultipartFile file

    static constraints = {
        id nullable: true
        nombre nullable: false
        file nullable: true
        accion inList: ['upload', 'delete', 'none'], validator: { val, obj, errors ->
            if (val == 'delete' && obj.id == null)
                errors.rejectValue('accion', 'error.accion.borrar.pero.id.null')

            if (val == 'upload' && obj.file == null)
                errors.rejectValue('accion', 'error.accion.subir.pero.file.null')
        }
    }

}
