-- ============================================================================
-- Supabase Table Creation Script for Food Logs
-- ============================================================================
-- This script creates the food_logs table for storing user food intake records
-- Run this in your Supabase SQL Editor before using the application
-- ============================================================================

-- Create the food_logs table
CREATE TABLE IF NOT EXISTS food_logs (
    -- Primary key
    id BIGSERIAL PRIMARY KEY,

    -- Foreign key to auth.users
    user_id UUID NOT NULL REFERENCES auth.users(id) ON DELETE CASCADE,

    -- Food details (from FoodDetails entity)
    food_id BIGINT NOT NULL,
    food_name TEXT NOT NULL,
    food_type TEXT,
    brand_name TEXT,
    food_url TEXT,

    -- Serving information (from ServingInfo entity)
    serving_id BIGINT NOT NULL,
    serving_description TEXT NOT NULL,
    serving_amount DOUBLE PRECISION NOT NULL,
    serving_unit TEXT NOT NULL,
    serving_multiplier DOUBLE PRECISION NOT NULL,

    -- Nutrition data (from ServingInfo entity)
    calories DOUBLE PRECISION,
    protein DOUBLE PRECISION,
    fat DOUBLE PRECISION,
    carbs DOUBLE PRECISION,
    fiber DOUBLE PRECISION,
    sugar DOUBLE PRECISION,
    sodium DOUBLE PRECISION,

    -- Log metadata
    meal TEXT NOT NULL CHECK (meal IN ('Breakfast', 'Lunch', 'Dinner', 'Snack')),
    logged_at TIMESTAMPTZ NOT NULL,
    created_at TIMESTAMPTZ DEFAULT now()
);

-- ============================================================================
-- Create Indexes for Performance
-- ============================================================================

-- Index for querying by user
CREATE INDEX IF NOT EXISTS idx_food_logs_user_id
    ON food_logs(user_id);

-- Index for querying by user and date (most common query pattern)
CREATE INDEX IF NOT EXISTS idx_food_logs_user_logged_at
    ON food_logs(user_id, logged_at DESC);

-- Index for querying by meal type
CREATE INDEX IF NOT EXISTS idx_food_logs_meal
    ON food_logs(user_id, meal);

-- ============================================================================
-- Enable Row Level Security (RLS)
-- ============================================================================

ALTER TABLE food_logs ENABLE ROW LEVEL SECURITY;

-- ============================================================================
-- RLS Policies: Users can only access their own food logs
-- ============================================================================

-- Policy: Users can view their own food logs
CREATE POLICY "Users can view their own food logs"
    ON food_logs
    FOR SELECT
    USING (auth.uid() = user_id);

-- Policy: Users can insert their own food logs
CREATE POLICY "Users can insert their own food logs"
    ON food_logs
    FOR INSERT
    WITH CHECK (auth.uid() = user_id);

-- Policy: Users can update their own food logs
CREATE POLICY "Users can update their own food logs"
    ON food_logs
    FOR UPDATE
    USING (auth.uid() = user_id);

-- Policy: Users can delete their own food logs
CREATE POLICY "Users can delete their own food logs"
    ON food_logs
    FOR DELETE
    USING (auth.uid() = user_id);

-- ============================================================================
-- Verification Query (run after creation to verify)
-- ============================================================================

-- Verify table was created
-- SELECT table_name, column_name, data_type
-- FROM information_schema.columns
-- WHERE table_name = 'food_logs'
-- ORDER BY ordinal_position;

-- ============================================================================
-- End of Script
-- ============================================================================

-- SUCCESS! Table is ready for use.
-- Your HealthZ app can now store food logs persistently in Supabase!
