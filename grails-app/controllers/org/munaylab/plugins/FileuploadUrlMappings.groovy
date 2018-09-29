package org.munaylab.plugins

class UrlMappings {

    static mappings = {
        "/archivo/$id/$nombre"(controller: "archivo", action:"show")
    }
}
