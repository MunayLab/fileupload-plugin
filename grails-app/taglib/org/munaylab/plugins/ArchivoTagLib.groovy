package org.munaylab.plugins

class ArchivoTagLib {
    static defaultEncodeAs = [taglib:'html']
    //static encodeAsForTags = [tagName: [taglib:'html'], otherTagName: [taglib:'none']]

    def archivoService

    def fileLink = { attrs ->
        Archivo archivo = attrs.file

        if (archivo && archivo.id && archivo.nombre) {
            out << g.createLink(url: "/archivo/${archivo.id}/${archivo.nombre}")
        } else {
            out << ""
        }
    }

}
