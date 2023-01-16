package exercise1

object Context {
  val startDate: String = {
    if (sys.env.contains("startDate"))
      sys.env("startDate")
    else
      throw new Exception("start date must not be empty")
  }

  val endDate: String = {
    if (sys.env.contains("endDate"))
      sys.env("endDate")
    else
      throw new Exception("end date must not be empty")
  }
}
