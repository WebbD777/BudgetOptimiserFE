package co.za.dylanwebb.budgetoptimiserfe

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform