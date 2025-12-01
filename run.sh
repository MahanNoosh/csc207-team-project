#!/bin/bash

# HealthZ Application Launcher
# This script sets up environment variables and runs the JavaFX application

echo "🚀 Starting HealthZ Application..."

# Set environment variables
export SUPABASE_URL="https://gutqsmmssgsttyxigawt.supabase.co"
export SUPABASE_ANON_KEY="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Imd1dHFzbW1zc2dzdHR5eGlnYXd0Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3MzI0NzYzNDcsImV4cCI6MjA0ODA1MjM0N30.QUdIDqaUPL-lB1kT0vMJT6TBUvtvRJ0H13SeDUx90vs"
export FATSECRET_CLIENT_ID="9ef37d375ad34d71a2e1f0703d79c93c"
export FATSECRET_CLIENT_SECRET="4b4cc13f88344df9a600a17af0bdc0a9"

# Run the application using Maven JavaFX plugin
mvn javafx:run
