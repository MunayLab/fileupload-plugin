package org.munaylab.plugins

class UrlMappings {

    static mappings = {
        "/$action/$id"(controller: "articulo"){
            constraints {
                // apply constraints here
            }
        }

        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

        "/"(view:"/index")
        "500"(view:'/error')
        "404"(view:'/notFound')
    }
}
