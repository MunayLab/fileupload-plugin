package org.munaylab.plugins

import grails.testing.gorm.DataTest
import grails.testing.web.controllers.ControllerUnitTest
import spock.lang.Specification

class ArchivoControllerSpec extends Specification
        implements DataTest, ControllerUnitTest<ArchivoController> {

    void setupSpec() {
        mockDomains Archivo
    }

    void setup() {
        controller.archivoService = Mock(ArchivoService)
    }

    void "descargar archivo"() {
        given:
        def archivo = crearArchivo
        1 * controller.archivoService.obtener(_) >> { archivo }
        when:
        controller.show(archivo.id)
        then:
        response.status == 200
        response.text == new String(archivo.fileBytes)
        response.contentType == "${archivo.fileContentType};charset=utf-8"
    }
    void "archivo no encontrado"() {
        given:
        1 * controller.archivoService.obtener(_) >> { null }
        when:
        controller.show(1)
        then:
        response.status == 404
    }

    private Archivo getCrearArchivo() {
        new Archivo().with {
            nombre          = 'archivo'
            fileBytes       = 'text'.bytes
            fileContentType = 'text/plain'
            it
        }.save(flush: true)
    }
}
