# Comprehensive Implementation Plan - Paisa Kamau

## Phase 1: Database & Backend (Data Models, Security Rules, Cloud Functions)
- Design Firestore schema (`users`, `tasks`, `transactions`, `withdrawals`, `referrals`, `configs`).
- Implement Firestore security rules for data isolation.
- Develop Cloud Functions for:
    - Reward verification (AdMob SSV).
    - Daily check-in/streak tracking.
    - Withdrawal request handling.
    - Referral bonus processing.

## Phase 2: Feature Implementation
- **Home (Video Tasks)**: Task listing, watch time enforcement, server-side reward.
- **Wallet & Withdrawal**: Balance management, conversion (R-to-NPR), withdrawal requests.
- **Gamification**: Daily rewards, Spin wheel, Quiz/Surveys.
- **Referral & Levels**: Unique code generation, referral tracking, level logic based on watch time.

## Phase 3: Anti-Fraud & Security
- Server-side reward calculation.
- Device-level account limiting.
- Anti-cheat logic (e.g., skip detection).

## Phase 4: Admin Panel & UX
- Admin Dashboard (Metrics, User management, Payout processing).
- Fine-tuning UI (Animations, Localization, Dark/Light theme).
- Testing and final deployment.
