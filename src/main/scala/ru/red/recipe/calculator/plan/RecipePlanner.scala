package ru.red.recipe.calculator.plan

import domain._

trait RecipePlanner {
  def findOptimal(
      recipes: Seq[Recipe],
      required: Seq[Component]
  ): Option[RecipePlan]
}
