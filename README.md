# Stock Trading Application with Spring Boot and GemFire

This is a Spring Boot application that demonstrates how to build a stock trading platform using GemFire (Apache Geode) as the data store. The application provides functionality for managing stocks, portfolios, and trade transactions with high performance and reliability.

## Features

- **Stock Management**: Create, read, update, and delete stock information
- **Portfolio Management**: Track user portfolios with holdings and cash balances
- **Trade Execution**: Execute buy and sell orders with proper validation
- **Real-time Data**: Store and retrieve stock data with high performance
- **RESTful API**: Comprehensive API for all trading operations

## Project Structure

- `src/main/java/com/example/gemfirestarter`: Java source files
  - `GemfireStarterApplication.java`: Main application class
  - `model/`: Entity classes (Stock, Portfolio, TradeTransaction)
  - `repository/`: GemFire repositories
  - `service/`: Business logic services
  - `controller/`: REST API controllers
  - `config/`: Configuration classes
- `src/main/resources`: Configuration files
  - `application.properties`: Spring Boot and GemFire configuration
- `src/test/java/com/example/gemfirestarter`: Test files

## Technology Stack

- **Spring Boot**: Application framework
- **Spring Data GemFire**: Data access layer
- **Apache Geode/GemFire**: In-memory data grid
- **Lombok**: Reduces boilerplate code
- **Spring Validation**: Input validation
- **Spring WebSocket**: For real-time updates (optional)

## Getting Started

### Prerequisites

- Java 11 or higher
- Gradle 7.4 or higher
- GemFire/Apache Geode (optional for production setup)

### Running the Application

1. Clone or download this repository

2. Build the project:
   ```
   ./gradlew build
   ```

3. Run the application:
   ```
   ./gradlew bootRun
   ```

The application will start on port 8080. You can access it at http://localhost:8080

### Development Mode

By default, the application runs with an embedded GemFire instance in local mode, which is suitable for development. Sample data is automatically loaded when running in non-production environments.

## API Endpoints

### Stocks

- `GET /api/stocks`: Get all stocks
- `GET /api/stocks/{symbol}`: Get stock by symbol
- `POST /api/stocks`: Create a new stock
- `PUT /api/stocks/{symbol}/price`: Update stock price
- `GET /api/stocks/search?query={query}`: Search stocks by company name
- `GET /api/stocks/price-range?minPrice={min}&maxPrice={max}`: Get stocks in price range
- `GET /api/stocks/top-volume`: Get top volume stocks
- `DELETE /api/stocks/{symbol}`: Delete a stock

### Portfolios

- `GET /api/portfolios/{userId}`: Get user's portfolio
- `POST /api/portfolios/{userId}?initialBalance={amount}`: Create a new portfolio
- `GET /api/portfolios/{userId}/value`: Get portfolio value
- `GET /api/portfolios/top`: Get top portfolios by value
- `GET /api/portfolios/by-stock/{stockSymbol}`: Get portfolios holding a specific stock

### Trades

- `POST /api/trades`: Create a new trade order
- `GET /api/trades?userId={userId}`: Get user's trade history
- `GET /api/trades/{id}`: Get trade by ID
- `PUT /api/trades/{id}/execute`: Execute a pending trade
- `PUT /api/trades/{id}/cancel`: Cancel a pending trade
- `GET /api/trades/pending?stockSymbol={symbol}`: Get pending trades for a stock
- `GET /api/trades/date-range?userId={userId}&startDate={start}&endDate={end}`: Get trades in date range

## Production Setup

For production environments:

1. Set up a GemFire/Apache Geode cluster
2. Configure the application to connect to the cluster by updating `application.properties`
3. Set the Spring profile to "prod" to disable sample data loading

## Testing

Run the tests with:
```
./gradlew test
```

## License

This project is licensed under the MIT License - see the LICENSE file for details.
