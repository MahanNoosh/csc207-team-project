package healthz.tut0301.group1.entities;

public record ServingSize(double amount, String unit) {

    public ServingSize {
        if (amount < 0) {
            throw new IllegalArgumentException("Serving size amount cannot be negative");
        }
        if (unit == null || unit.isBlank()) {
            throw new IllegalArgumentException("Serving size unit cannot be empty");
        }
    }

    public double getAmount() {
        return amount;
    }

    public String getUnit() {
        return unit;
    }

    public ServingSize scale(double factor) {
        return new ServingSize(amount * factor, unit);
    }

    public ServingSize add(ServingSize other) {
        if (other == null) return this;

        if (this.unit.equalsIgnoreCase(other.unit)) {
            return new ServingSize(this.amount + other.amount, this.unit);
        }

        return new ServingSize(0, "various");
    }

    @Override
    public String toString() {
        return amount + unit;
    }
}