package org.munaylab.plugins

import grails.testing.web.UrlMappingsUnitTest
import spock.lang.Specification

class ArchivoUrlMappingsSpec extends Specification implements UrlMappingsUnitTest<UrlMappings> {

    void setup() {
        mockController(ArchivoController)
    }
    void "test forward mappings"() {
        expect:
        verifyForwardUrlMapping("/archivo/1234/imagen.jpg", controller: 'archivo', action: 'show') {
            id      = '1234'
            nombre  = 'imagen.jpg'
        }
        and:
        assertForwardUrlMapping("/archivo/1234/imagen.jpg", controller: 'archivo', action: 'show') {
            id      = '1234'
            nombre  = 'imagen.jpg'
        }
    }
}
