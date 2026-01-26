# HealthZ - Comprehensive Health & Nutrition Tracking Application

![Java](https://img.shields.io/badge/Java-17-orange)
![JavaFX](https://img.shields.io/badge/JavaFX-21-blue)
![Clean Architecture](https://img.shields.io/badge/Architecture-Clean-green)
![License](https://img.shields.io/badge/License-MIT-yellow)

A comprehensive health and nutrition tracking application built with Clean Architecture principles, enabling users to discover recipes, track activities, and manage personalized health goals.

[Figma Design](https://www.figma.com/design/JOtTuUqmnF8bVy280Rmg1g/CSC207-HealthZ?node-id=0-1&t=rXSVEdYzSCTHGX1b-1)

## Home
![Home Page](./src/main/resources/images/home_page.png)

---

## Onboarding / Sign Up Flow
![Sign Up – Step 1](./src/main/resources/images/signup1.jpg)
![Sign Up – Step 2](./src/main/resources/images/signup2.jpg)
![Sign Up – Step 3](./src/main/resources/images/signup3.jpg)
![Sign Up – Step 4](./src/main/resources/images/signup4.jpg)
![Sign Up – Step 5](./src/main/resources/images/signup5.jpg)

---

## Search & Discovery
![Search Filter](./src/main/resources/images/search_filter.png)
![Search Results](./src/main/resources/images/search_food_name.png)

---

## Nutrition Information
![Nutrition Lookup](./src/main/resources/images/nutrition_lookup.jpg)
![Food Detail](./src/main/resources/images/food_detail.jpg)

---

## Tracking
![Food Log](./src/main/resources/images/food_log.jpg)
![Activity Tracker](./src/main/resources/images/activity_tracker.jpg)

---

## 🌟 Features

### 🍳 Recipe Search & Discovery
- Search 100,000+ recipes via FatSecret API
- Advanced filtering by macronutrient percentages (carbs, protein, fat)
- Real-time filtering with interactive sliders
- Detailed recipe views with ingredients, instructions, and nutrition facts

### ⭐ Favorite Recipes Management
- Save favorite recipes with one click
- Persistent storage using Supabase database
- Quick access to saved recipes across sessions
- Delete favorites with automatic UI refresh

### 🏃 Activity Tracking
- Log exercises from comprehensive exercise database
- Automatic calorie calculation based on user profile (weight, age, sex)
- Activity history with date tracking
- Integration with personalized dashboard

### 📊 Personalized Dashboard
- Daily calorie goal calculation using Mifflin-St Jeor equation
- Goal-adjusted recommendations (weight loss, maintenance, muscle gain)
- Activity history widget with recent exercises
- Real-time calorie tracking and progress visualization

### 👤 User Profile Management
- Secure authentication via Supabase Auth
- Customizable health profiles (height, weight, age, sex, activity level)
- Goal setting (weight loss, maintenance, muscle gain)
- Diet preferences and health conditions tracking
