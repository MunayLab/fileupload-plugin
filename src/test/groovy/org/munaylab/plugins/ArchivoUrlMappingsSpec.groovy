package org.munaylab.plugins

import grails.testing.web.UrlMappingsUnitTest
import spock.lang.Specification

class ArchivoUrlMappingsSpec extends Specification implements UrlMappingsUnitTest<UrlMappings> {

    void setup() {
        mockController(ArchivoController)
    }
    void "test forward mappings"() {
        expect:
        verifyForwardUrlMapping("/archivo/show/1", controller: 'archivo', action: 'show') { id = '1' }
        and:
        assertForwardUrlMapping("/archivo/show/1", controller: 'archivo', action: 'show') { id = '1' }
    }
}
