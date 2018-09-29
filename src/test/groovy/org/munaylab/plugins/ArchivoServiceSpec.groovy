package org.munaylab.plugins

import org.springframework.mock.web.MockMultipartFile

import grails.testing.gorm.DataTest
import grails.testing.services.ServiceUnitTest
import spock.lang.Specification

class ArchivoServiceSpec extends Specification
        implements DataTest, ServiceUnitTest<ArchivoService>{

    void setupSpec() {
        mockDomains Archivo
    }

    void "subir archivo"() {
        given: 'command listo para subir'
        def command = new ArchivoCommand().with {
            nombre  = 'archivo'
            accion  = 'upload'
            file    = mockFile
            it
        }
        expect: 'operacion sin errores'
        !service.subir(command).hasErrors()
        and: 'archivo guardado en db'
        Archivo.count() == 1
    }
    void "sobreescribir un archivo"() {
        given: 'creamos un archivo'
        def archivo = crearArchivo
        def id = archivo.id
        and: 'command listo para subir'
        def command = new ArchivoCommand().with {
            nombre  = 'nuevo archivo'
            accion  = 'upload'
            file    = mockFileUpdated
            it
        }
        command.id = archivo.id
        when: 'subimos el nuevo archivo'
        archivo = service.subir(command)
        then: 'archivo guardado en db'
        Archivo.count() == 1
        and: 'contenido del archivo modificado'
        archivo.id == id
        archivo.nombre == 'nuevo archivo'
        archivo.fileBytes == 'text updated'.bytes
    }
    void "subir archivo vacio"() {
        given: 'preparamos un command sin archivo'
        def command = new ArchivoCommand().with {
            nombre  = 'archivo'
            accion  = 'upload'
            it
        }
        when: 'intentamos subir el command'
        def archivo = service.subir(command)
        then: 'obtenemos un global error'
        archivo.hasErrors()
        archivo.errors.globalError.code == 'error.accion.subir.pero.file.null'
        and: 'el archivo no se guardo en db'
        Archivo.count() == 0
    }
    void "archivo borrado"() {
        given: 'creamos un archivo'
        def archivo = crearArchivo
        and: 'preparamos un command para borrar'
        def command = new ArchivoCommand().with {
            id      = archivo.id
            nombre  = 'archivo'
            accion  = 'delete'
            it
        }
        when: 'borramos un archivo'
        archivo = service.borrar(command)
        then:
        Archivo.count() == 0
        archivo == null
    }
    void "borrar archivo"() {
        given: 'preparamos un command para borrar'
        def command = new ArchivoCommand().with {
            id      = crearArchivo.id
            nombre  = 'archivo'
            accion  = 'delete'
            it
        }
        expect: 'se borró el archivo de db'
        !service.borrar(command) && Archivo.count() == 0
    }
    void "borrar archivo sin id"() {
        given: 'preparamos un command sin id'
        def command = new ArchivoCommand().with {
            nombre  = 'archivo'
            accion  = 'delete'
            it
        }
        when: 'intentamos borrar'
        def archivo = service.borrar(command)
        then: 'obtenemos un gloabl error'
        archivo.hasErrors()
        archivo.errors.globalError.code == 'error.accion.borrar.pero.id.null'
    }

    private Archivo getCrearArchivo() {
        new Archivo(nombre: 'archivo', fileBytes: 'text'.bytes, fileContentType: 'text/plain').save(flush: true)
    }

    private MockMultipartFile getMockFile() {
        new MockMultipartFile('data', 'text.txt', 'text/plain', 'text'.bytes)
    }

    private MockMultipartFile getMockFileUpdated() {
        new MockMultipartFile('data', 'textupdated.txt', 'text/plain', 'text updated'.bytes)
    }

}
