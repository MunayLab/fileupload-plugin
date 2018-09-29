package org.munaylab.plugins

class Archivo {

    String nombre
    byte[] fileBytes
    String fileContentType

    static constraints = {
        fileBytes nullable: false
        fileContentType nullable: false
    }

    static mapping = {
        fileBytes column: 'file_bytes', sqlType: 'longblob'
    }

}
