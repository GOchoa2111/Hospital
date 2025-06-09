namespace HospitalSistemaAPI.Configuration
{
    // Esta clase sirve para mapear los datos del archivo appsettings.json (sección Jwt)
    public class JwtSettings
    {
        public string Key { get; set; }           // Clave secreta para firmar el token
        public string Issuer { get; set; }        // Emisor del token
        public string Audience { get; set; }      // Receptor del token
        public int ExpireMinutes { get; set; }    // Minutos antes de que expire el token
    }
}
