package org.munaylab.plugins

import grails.testing.web.taglib.TagLibUnitTest
import spock.lang.Specification

class ArchivoTagLibSpec extends Specification implements TagLibUnitTest<ArchivoTagLib> {

    void "crear link"() {
        given:
        def archivo = new Archivo().with {
            id      = 1
            nombre  = 'file.jpeg'
            it
        }
        expect:
        tagLib.fileLink(file: archivo) == '/archivo/1/file.jpeg'
    }
    void "link invalido"() {
        given:
        def archivo = new Archivo().with {
            nombre  = 'file.jpeg'
            it
        }
        expect:
        tagLib.fileLink(file: archivo) == ''
    }

}
