package zero27zen.com.model

class User{

    var uId:String?=null
    var name:String?=null

    var hp:String?=null
    var alamat:String?=null
    var gambaruser:String?=null

    constructor() {}

    constructor(uId: String,name: String,hp:String, alamat:String,gambaruser: String) {
        this.uId = uId
        this.name = name
        this.gambaruser=gambaruser
        this.hp=hp
        this.alamat=alamat
    }

    constructor(name: String,hp:String,alamat: String) {
        this.name = name
        this.hp=hp
        this.alamat=alamat
    }
    constructor(name: String,hp:String, alamat:String,gambaruser:String) {
        this.name = name
        this.hp=hp
        this.gambaruser=gambaruser
        this.alamat=alamat
    }

    fun toMap(): Map<String, Any> {

        val result = HashMap<String, Any>()
        result.put("Alamat", alamat!!)
        result.put("Gambar",gambaruser!!)
        result.put("Nama", name!!)
        result.put("NoHp",hp!!)


        return result
    }
    fun toMaps(): Map<String, Any> {

        val result = HashMap<String, Any>()
        result.put("Nama", name!!)
        result.put("NoHp",hp!!)
        result.put("Alamat", alamat!!)


        return result
    }
}