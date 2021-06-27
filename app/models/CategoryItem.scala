package models

import play.api.libs.json.{Format, Json}
import play.modules.reactivemongo._
import reactivemongo.bson._
import reactivemongo.play.json._

case class CategoryItem (
                          _id: Option[BSONObjectID],
                          title: String,
                          description: String,
                          items: Array[Item],
//                          output: Array[Item]
                        )
object CategoryItem {
  implicit val categoryItemFormat: Format[CategoryItem] = Json.format[CategoryItem]

  implicit val categoryItemWriter: BSONDocumentWriter[CategoryItem] = Macros.writer[CategoryItem]
  implicit val categoryItemReader: BSONDocumentReader[CategoryItem] = Macros.reader[CategoryItem]
}