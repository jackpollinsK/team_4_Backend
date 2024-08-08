INSERT INTO jobRoles (role_Name, location_id, capability_id, band_id, closingDate, description, responsibilities, jobSpec, openPositions)
SELECT
    CONCAT(c.capability_name, ' ', b.band_name, ' Role') AS roleName,
    l.id AS location_id,
    c.id AS capability_id,
    b.id AS band_id,
    '2024-12-31' AS closingDate,
    CONCAT('Job description for ', c.capability_name, ' in ', l.location_name, ' at ', b.band_name, ' level.') AS description,
    CONCAT('Responsibilities for ', c.capability_name, ' role.') AS responsibilities,
    'https://kainossoftwareltd.sharepoint.com/people/SitePages/Career-Lattice.aspx' AS jobSpec,
    1 AS openPositions
FROM
    location l
    CROSS JOIN capability c
    CROSS JOIN band b;