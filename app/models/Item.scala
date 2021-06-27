package models

import play.api.libs.json.{Format, Json}
import play.modules.reactivemongo._
import reactivemongo.bson._
import reactivemongo.play.json._

case class Item(
               _id: Option[BSONObjectID],
               name: String,
               description: String
               )
object Item {
  implicit val itemFormat: Format[Item] = Json.format[Item]

  implicit val itemWriter: BSONDocumentWriter[Item] = Macros.writer[Item]
  implicit val itemReader: BSONDocumentReader[Item] = Macros.reader[Item]

//  implicit object ItemBSONReader extends BSONDocumentReader[Item] {
//    def read(doc: BSONDocument): Item = {
//      Item(
//        doc.getAs[BSONObjectID]("_id"),
//        doc.getAs[String]("name").get,
//        doc.getAs[String]("description").get
//      )
//    }
//  }
//
//  implicit object ItemBSONWriter extends BSONDocumentWriter[Item] {
//    def write(item: Item): BSONDocument = {
//      BSONDocument(
//        "_id" -> item._id,
//        "name" -> item.name,
//        "description" -> item.description
//      )
//    }
//  }
}