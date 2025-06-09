using Microsoft.AspNetCore.Identity;

namespace HospitalSistemaAPI.Helpers
{
    public static class PasswordHelper
    {
        private static PasswordHasher<string> hasher = new PasswordHasher<string>();

        // Método para convertir una contraseña en hash
        public static string HashPassword(string password)
        {
            return hasher.HashPassword(null, password);
        }

        // Método para comparar una contraseña con su hash
        public static bool VerifyPassword(string hashedPassword, string inputPassword)
        {
            var result = hasher.VerifyHashedPassword(null, hashedPassword, inputPassword);
            return result == PasswordVerificationResult.Success;
        }
    }
}
