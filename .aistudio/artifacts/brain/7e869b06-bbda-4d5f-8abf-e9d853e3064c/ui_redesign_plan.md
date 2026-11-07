# UI Redesign Implementation Plan - Paisa Kamau

Based on the provided video, the app currently features:
- Bottom Navigation (Home, Ads, Wallet, Profile)
- Home: Simple math puzzles for rewards.
- Wallet: Balance display, withdrawal methods (UPI, Bank, etc.).
- Profile: User details, account status.

## UI/UX Redesign Strategy (Material 3)

### 1. Navigation Shell
- Implement a `Scaffold` with a modern `NavigationBar`.
- Use M3 `NavigationBarItem` for cleaner icons and labels.

### 2. Home Dashboard (Math Puzzles)
- Elevate the puzzle card with a card container, subtle shadow, and clean typography.
- Use a distinct color for the "Solve" action.
- Add a progress indicator for daily earning goals.

### 3. Wallet Screen
- Redesign the balance display as a prominent card with a gradient background (using the established fintech palette).
- Group withdrawal request inputs into a single, clean form section.
- Use distinct icons for payment methods (e.g., eSewa, Khalti).

### 4. Profile Screen
- Redesign the user card (avatar, name, email).
- Use `OutlinedCard` for details section for a cleaner look.
- Use M3 `FilledButton` for the "Support" and "Sign Out" actions.

### 5. General Polish
- Apply consistent spacing (8dp grid) across all screens.
- Implement smooth transitions using `AnimatedContent`.
- Apply Material 3 dynamic color theme where applicable.
